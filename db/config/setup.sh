#!/bin/bash

MOUNT_DIR=/docker-entrypoint-initdb.d

P_CMD="psql -U ${POSTGRES_USER} -d ${POSTGRES_DB}"
SQL_DIR=${MOUNT_DIR}/sql
DATA_DIR=${MOUNT_DIR}/data

## テーブル作成
${P_CMD} -f ${SQL_DIR}/01_01_users.sql

## データ作成
${P_CMD} -c "\copy users from ${DATA_DIR}/users.csv delimiter ',' csv;"