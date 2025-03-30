FROM python:3.11-slim

COPY . .

RUN apt-get update && apt-get install -y \
    coreutils \
    gcc \
    bash \
    python3-dev \
    build-essential \
    --no-install-recommends

RUN pip install -r ./requirements.txt

CMD ["python", "main.py"]
