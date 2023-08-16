# 포팅 메뉴얼

## 목차

1. [프로젝트 사용 도구](#1-프로젝트-사용-도구)
2. [기술스택](#2-기술스택)
3. [빌드](#3-빌드)
4. [배포](#4-배포)

## 1. 프로젝트 사용 도구

- 이슈 관리 : JIRA
- 형상 관리 : Git, GitLab
- 커뮤니케이션 : Notion, Mattermost
- 디자인 : Figma
- UCC : Adobe

## 2. 기술스택

- IntelliJ : 2023.1.3
- Visual Studio Code : 1.81.1
- JVM : 11.0.20
- Vue : 3.2.13
- SpringBoot : 2.7.14
- Node.js : 18.16.1
- DBMS : MySQL 8.0.34
- AWS : Ubuntu 20.04 LTS
- Nginx : 1.18.0
- Docker : 20.10.21
- Gradle : 8.1.1
- Certbot : 2.6.0

## 3. 빌드

```bash
git clone https://lab.ssafy.com/s09-webmobile1-sub2/S09P12A210.git
cd S09P12A210
```

### 3.1 환경변수

#### Spring application.yml

```javascript
#Spring 설정
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{{DB IP}}:{{DB Port}}/music?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: {{DataBase Id}}
    password: {{DataBase Password}}
  mvc:
    servlet:
      path: /api
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB


  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      use-new-id-generator-mappings: false
    database-platform: com.quokka.classmusic.common.util.MySQLDialect


    properties:
      hibernate.format_sql: true
      dialect: org.hibernate.dialect.MySQL8InnoDBDialect
  mail:
    protocol: smtps
    host: smtp.gmail.com
    port: 465
    username: {{Email}}
    password: {{Gmail Smtp App Pw}}
    default-encoding: utf-8
    properties:
      mail:
        stmp:
          starttls:
            enable: true
          auth: true


logging:
  level:
    com.quokka.classmusic: debug
    org.hibernate.SQL: debug
    org.hibernate.type.descriptor.sql.BasicBinder: trace
    com:
      amazonaws:
        util:
          EC2MetadataUtils: error

server:
  port: 3000

# jwt
jwt:
  secret: {{Jwt 시크릿 키}}
  issuer: quokka
  # unit is ms. 30 * 60 * 1000 = 30 minutes
  access-expiration: 604800000
  # unit is ms. 7 * 24 * 60 * 60 * 1000 = 7 days
  refresh-expiration: 604800000
#  refresh-expiration: 60000

#OPENVIDU 설정
OPENVIDU_URL: https://localhost:8443/
OPENVIDU_SECRET: MY_SECRET

#AWS S3 설정
cloud:
  aws:
    S3:
      bucket: {{S3 버킷 이름}}
    region:
      static: ap-northeast-2
      auto: false
    stack:
      auto: false
    credentials:
      access-key: {{S3 키}}
      secret-key: {{S3 시크릿 키}}
```

### 3.2 백엔드

```bash
cd backend/classmusic/
gradle build
```

### 3.3 프론트엔드

```bash
cd frontend/classmusic/
npm i
npm run build
```

## 4. 배포

#### 4.1 서버 Port 구성

- nginx : 80:80, 443:443
- SpringBoot : 3000:3000
- MySQL : 3306:3306
- OpenVidu
  - TCP : 22:22, 8443:8443
  - TCP + UDP : 3478:3478, 40000~57000, 57001 ~ 65535

#### 4.2 서버 설정

```bash
sudo ufw allow 22
sudo ufw allow 80
sudo ufw allow 443
sudo ufw allow 3478 tcp
sudo ufw allow 3478 udp
sudo ufw allow 40000:57000 tcp
sudo ufw allow 40000:57000 udp
sudo ufw allow 57001:65535 tcp
sudo ufw allow 57001:65535 udp
sudo ufw status
```

#### 4.3 OpenVidu 설정

```bash
Docker 와 Docker Compose가 설치되어 있어야 함
sudo su
cd /opt
curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash
cd /opt/openvidu
vi .env

//.env 파일 수정
DOMAIN_OR_PUBLIC_IP=도메인
OPENVIDU_SECRET=시크릿 키
CERTIFICATE_TYPE=letsencrypt
LETSENCRYPT_EMAIL=이메일
HTTP_PORT=8081
HTTPS_PORT=8443

//수정후 openvidu 시작
./openvidu start &
```

#### 4.4 Nginx 설정

default 파일 수정

```bash
sudo vim /etc/nginx/sites-available/default
```

nginx default 파일

```bash
server {
        root /var/www/html/dist;

        # Add index.php to the list if you are using PHP
				index index.html index.htm index.nginx-debian.html;
        server_name i9a210.p.ssafy.io; # managed by Certbot

        location /api {
                proxy_pass https://i9a210.p.ssafy.io:3000;

        }

        location / {
                try_files $uri $uri /index.html;

        }

		    listen [::]:443 ssl ipv6only=on; # managed by Certbot
				listen 443 ssl; # managed by Certbot
		    ssl_certificate /etc/letsencrypt/live/i9a210.p.ssafy.io/fullchain.pem; # managed by Certbot
		    ssl_certificate_key /etc/letsencrypt/live/i9a210.p.ssafy.io/privkey.pem; # managed by Certbot
		    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
		    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}
server {
    if ($host = i9a210.p.ssafy.io) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


        listen 80 ;
        listen [::]:80 ;
    server_name i9a210.p.ssafy.io;
    return 404; # managed by Certbot
}
```

이후 nginx 시작

```bash
sudo systemctl start nginx
```

#### 4.5 Certbot 설정

```bash
sudo add-apt-repository ppa:certbot/certbot
sudo apt install python-certbot-nginx
sudo certbot --nginx -d example.com
//설치시 이메일 , 약관동의 , http > https redirect 설정
```
