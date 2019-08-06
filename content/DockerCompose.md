# DockerCompose
Docker Copose는 여러개의 Docker를 구성하고 Container를 동시에 실행 시킬 수 있는 도구입니다. YAML 문법을 사용하여 작성하며, Docker Compose up 이라는 단일 명령으로 여러개의 Container를 한번에 실행 시킬 수 있습니다. 

# Docker Compose의 특징
 - 각 Container 들의 환경변수, 포트, 각 Container 간의 종속성 및 실행 순서 등을 제어 할 수 있습니다.
 - 변경이 없는 Container를 실행할 경우에는 기존의 Container를 재사용 합니다.
 - Container에 변경이 있을 경우, 기존의 Container를 찾아 새로운 Container로 해당 볼륨을 복사하여 Data를 유지합니다.
 - Project 이름을 사용하여 단일 환경 내에서 서로 격리된 환경을 구성 할 수 있습니다. 환경변수를 사용하여 Project 이름을 지정 할 수 있으며, 고유의 빌드 번호로도 부여 할 수 있습니다.

# Docker Compose 설치 (Linux Ubuntu)
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

# Docker Compose 권한 설정
```
sudo chmod +x /usr/local/bin/docker-compose
```

# Docker Compose 를 이용하여 Elastic Stack 실행
```
https://github.com/deviantony/docker-elk 참고하여 제작 (수정중)
```

# Volume 설정을 통해 외부 Repository 지정
```
내용 추가 필요
```

# 기존 Elastic Stack 에 Grafana 추가 하기
```
Docker Compose 파일 수정
```

