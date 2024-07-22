#!/bin/bash

# Cria diretório para armazenar os certificados
mkdir -p certs
cd certs

# Gera chave privada e certificado autoassinado
keytool -keystore kafka.server.keystore.jks -alias localhost -keyalg RSA -validity 365 -genkey -dname "CN=localhost, OU=Test, O=Test, L=Test, S=Test, C=US" -storepass password -keypass password

# Gera um certificado de confiança
keytool -keystore kafka.server.truststore.jks -alias CARoot -keyalg RSA -validity 365 -genkey -dname "CN=CARoot, OU=Test, O=Test, L=Test, S=Test, C=US" -storepass password -keypass password

# Exporta o certificado do servidor
keytool -keystore kafka.server.keystore.jks -alias localhost -certreq -file cert-file -storepass password

# Assina o certificado
keytool -keystore kafka.server.truststore.jks -alias CARoot -gencert -infile cert-file -outfile cert-signed -validity 365 -storepass password

# Importa o certificado assinado de volta para o keystore do servidor
keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file cert-signed -storepass password -noprompt
keytool -keystore kafka.server.keystore.jks -alias localhost -import -file cert-signed -storepass password -noprompt

# Importa o certificado da CA para o truststore
keytool -keystore kafka.server.truststore.jks -alias CARoot -import -file cert-signed -storepass password -noprompt

cd ..
