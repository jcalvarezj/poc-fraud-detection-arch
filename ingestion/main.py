import concurrent.futures

from kafka import consume_messages


if __name__ == "__main__":
    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
        future = executor.submit(consume_messages)
        future.result()
