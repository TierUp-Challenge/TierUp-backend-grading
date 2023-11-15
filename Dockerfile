FROM python

WORKDIR /python

COPY judge.sh /python

ENTRYPOINT [ "bash", "judge.sh" ]