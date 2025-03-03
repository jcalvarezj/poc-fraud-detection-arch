from pydantic import BaseModel
from datetime import datetime


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


class Transaction(BaseModel):
    transaction_id: str
    sender_bank_account: str
    sender_details: User
    receiver_bank_account: str
    receiver_details: User
    amount: float
    status: str
    evaluation: str
    transfer_date: datetime
    sender_bank: str

class TransactionAVRO(BaseModel):
    transaction_id: str
    sender_bank_account: str
    sender_user_id: str
    receiver_bank_account: str
    receiver_user_id: str
    amount: float
    status: str
    evaluation: str
    transfer_date: datetime
    sender_bank: str
