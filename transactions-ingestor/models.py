from pydantic import BaseModel


class User(BaseModel):
    id: str
    name: str
    address: str
    email: str
    birthdate: str
    phone_number: str
    job: str
    company: str
    ssn: str
    blood_group: str
    website: str
    username: str
    bank_account: str


class TransactionAVRO(BaseModel): # TODO - Usar para etapa de procesamiento
    transaction_id: str
    sender_bank_account: str
    sender_user_id: str
    receiver_bank_account: str
    receiver_user_id: str
    amount: float
    status: str
    evaluation: str
    transfer_date: str
    sender_bank: str


class Transaction(BaseModel):
    transaction_id: str
    sender_bank_account: str
    sender_details: User
    receiver_bank_account: str
    receiver_details: User
    amount: float
    status: str
    evaluation: str
    transfer_date: str
    sender_bank: str

    def to_avro_model(self) -> TransactionAVRO:
        avro_data = {}

        for field in TransactionAVRO.__annotations__:
            if field in self.__dict__:
                value = getattr(self, field)
                avro_data[field] = value

        avro_data["sender_user_id"] = self.sender_details.id
        avro_data["receiver_user_id"] = self.receiver_details.id

        return TransactionAVRO(**avro_data)
