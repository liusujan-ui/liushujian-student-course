FROM maven:3.9-eclipse-temurin-17

# 常用开发工具
RUN apt-get update && apt-get install -y vim git curl

# Hadoop CLI（可选，方便在容器内直接 hdfs dfs 操作）
RUN curl -sSL https://dlcdn.apache.org/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz | \
    tar xz -C /opt && \
    ln -s /opt/hadoop-3.3.6 /opt/hadoop

ENV HADOOP_HOME=/opt/hadoop
ENV HADOOP_CONF_DIR=/opt/hadoop/etc/hadoop
ENV PATH=/opt/hadoop/bin:$PATH

WORKDIR /project
CMD ["/bin/bash"]
