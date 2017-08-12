


使用如下命令生成key
#keytool -genkeypair -alias thejwttokenkey  -keyalg RSA -keypass hellojwttoken -keystore oauthserver.jks -storepass hellojwttoken
 
使用如下命令生成public.txt                 
#keytool -list -rfc --keystore oauthserver.jks | openssl x509 -inform pem -pubkey


