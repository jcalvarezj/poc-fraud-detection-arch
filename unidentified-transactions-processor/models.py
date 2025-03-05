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
    source_process: str = "kafka-streams"


class TransactionResult(BaseModel):
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
    source_process: str = "kafka-streams"


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

    def to_transaction_result(self) -> TransactionResult:
        transaction_result_data = {}

        for field in TransactionResult.__annotations__:
            if field in self.__dict__:
                value = getattr(self, field)
                transaction_result_data[field] = value

        transaction_result_data["sender_user_id"] = self.sender_details.id
        transaction_result_data["receiver_user_id"] = self.receiver_details.id

        return TransactionResult(**transaction_result_data)

    def get_user_data(self, is_sender: bool = False):
        prefix = "sender" if is_sender else "receiver"
        user_details: User = getattr(self, f"{prefix}_details")
        return User(**user_details.model_dump())
