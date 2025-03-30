from pydantic import BaseModel


class User(BaseModel):
    """
    Represents a record of a user associated with a transaction
    """
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
    """
    Represents a "transaction-only" record of a transaction (insteed
    of user sub-models it only contains the user ids)
    """
    transaction_id: str
    sender_bank_account: str
    sender_id: str
    receiver_bank_account: str
    receiver_id: str
    amount: float
    status: str
    evaluation: str
    transfer_date: str
    sender_bank: str
    source_process: str = "kafka-streams"


class Transaction(BaseModel):
    """
    Represents a transaction record with its associated sender and receiver
    user data (sub-models)
    """
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
        """
        Converts a Transaction instance into a TransactionResult instance

        Returns:
            - A TransactionResult instance with the original Transaction
            instance data and user ids
        Raises:
            - Exception: Any uncaught exception
        """
        transaction_result_data = {}

        for field in TransactionResult.__annotations__:
            if value := getattr(self, field, None):
                transaction_result_data[field] = value

        transaction_result_data["sender_id"] = self.sender_details.id
        transaction_result_data["receiver_id"] = self.receiver_details.id

        return TransactionResult(**transaction_result_data)

    def get_user_data(self, is_sender: bool = False) -> User:
        """
        Returns a populated User instance with all the corresponding data from
        the Transaction instance. Fits attributes respectively for sender or
        receiver users

        Args:
            - is_sender (bool): Indicates whether the returned User corresponds
            to a sender or a receiver user
        Raises:
            - Exception: Any uncaught exception
        """
        prefix = "sender" if is_sender else "receiver"
        user_details: User = getattr(self, f"{prefix}_details")
        return User(**user_details.model_dump())
