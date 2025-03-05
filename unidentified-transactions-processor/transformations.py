from models import Transaction
from pydantic import BaseModel

def model_dump_fix_keys(model: BaseModel):
    return {
        key.upper(): value for key, value 
        in model.model_dump().items()
    }

def extract_transaction_data(message: dict):
    transaction = Transaction.model_validate(message)
    transaction_result = transaction.to_transaction_result()
    print(f"Sending transaction data!")
    print(transaction_result)
    return model_dump_fix_keys(transaction_result)

def extract_user_data(message: dict, is_sender: bool = False):
    transaction = Transaction.model_validate(message)
    user = transaction.get_user_data(is_sender)
    print(f"Sending {'sender' if is_sender else 'receiver'} user data!")
    print(user)
    return model_dump_fix_keys(user)
