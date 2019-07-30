# Docker

응용 프로그램들을 소프트웨어 컨테이너 안에 배치시켜 배포 및 실행을 위한 경량화 패키지입니다.

개발환경 등의 모든 종속성을 패키지화 하여 격리된 가상공간만을 사용하기 때문에, OS 전체를 가상화 하는 방식보다 가볍고 빠르게 동작합니다.
각 Container 들은 완전히 격리된 공간을 구성하며, File System의 가상화만 이루어집니다.

# Docker Image & Docker Container
Docker Image는 소프트웨어, 라이브러리, 환경 등을 설치한 후 배포가능한 패키지를 말하며, 
Docker Container는 Docker Image를 실행 한 형태입니다. 즉 Image는 실행 할 수 있는 패키지로 볼 수 있고, Container는 실행된 각각의 프로세스로 볼 수 있습니다. 또한 하나의 Image는 여러개의 Container로 실행이 가능합니다.



-----------------------
내용 추가 중 Docker layer
https://medium.com/@jessgreb01/digging-into-docker-layers-c22f948ed612

-------------------------


# Docker Hub
Docker Image들의 저장소인 Docker Hub는 현재 많은 사람들에 의해 Image가 제작 및 공유되고 있습니다. ubuntu, gradle 등의 SW 들 또한 등록되어 있고, 이러한 Image들은 Official Image로 표기하고 있습니다. 그리고 Official Image들은 Docker 환경에 맞게 재구성하여 경량화(Ubuntu의 경우 약 30MB) 되어 있습니다. 
개인 사용자 또한  용도에 맞게 선택하여 Customizing 이 가능하며, Docker Hub의 중앙저장소에 쉽게 올릴 수 있고(push), 또 필요한 Image를 받을 수 있습니다. (pull) 또한 받은 Image를 수정하여 또 다른 Image를 생성 할 수 있으며, 이 때 생성된 Image는 Base Image 에서 변경된 부분만을 저장하게 됩니다.


# Docker 설치
>
>설치 Test 후 추가 예정
>


# Docker Image 내려받기
지금부터 Hub를 통해 Docker 용 Ubuntu를 내려받아 보도록 하겠습니다. 
```
docker pull ubuntu
```

# Docker 실행
```
docker run <image ID>
```

# Docker run 과 Docker exec
run 을 통해 Image를 실행하게 되면 새로운 ID의 Container를 생성하게 됩니다. 1개의 Image로 여러개의 Conatiner를 만들 수 있으며,
만들어진 Container 들은 `Docker container ls` 명령을 통해 List를 확인 할 수 있습니다.
기존의 Container 를 실행하기 위해서는 List에서 Container ID를 이용하여 재사용이 가능합니다.
`Docker exec <Container ID>` 

# 외부 File을 Docker Container로 복사하기
Docker를 사용하다 보면 download나 설치 외 Local PC의 자료들을 복사해야 할 경우도 있습니다. 이런 경우 cp 명령을 통해 외부의 파일을 Container 내부로 복사 할 수 있으며, 명령은 아래와 같습니다. 복사의 대상이 파일일 경우 대상 파일만 복사되고, 폴더일 경우 하위 모든 파일까지 복사됩니다.
Docker cp <외부 경로> <Container ID:내부경로>
앞서 내려받은 Ubuntu Container에 


# Docker Hub Login 
>
> Linux 에서 테스트 후 추가 예정
>

# Docker Image Upload 
이번에는 앞에서 작업한 Docker Ubuntu Container를 Docker Hub로 업로드 해보도록 하겠습니다.
```Docker push <Container ID>```
>
> 내용 수정중
>


# Android Build 용 Docker Image 제작
Android build CI 구성을 위한 Docker Image 작성
지금부터는 이미 존재하는 Ubuntu의 Image를 기반으로 Android 빌드 환경의 Docker Image를 제작해 보겠습니다.


```
FROM ubuntu:latest

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

ENV http_proxy <proxy:port>
ENV https_proxy <proxy:port>

RUN chmod g+rx,o+rx /

RUN apk update && \
apt add unzip && \
apt add wget && \
apt add vim && \
apt add curl && \
apt add --no-cache openjdk8

RUN apt add --no-cache so:libnss3.so

ADD https://services.gradle.org/distributions/gradle-5.3-all.zip /opt/
RUN unzip /opt/gradle-5.3-all.zip -d /opt/gradle
ENV GRADLE_HOME /opt/gradle/gradle-5.3
ENV PATH $GRADLE_HOME/bin:$PATH
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

RUN chmod +x /opt/gradle/gradle-5.3/bin/gradle

RUN apt add python3
RUN apt add git

ARG ANDROID_SDK_VERSION=4333796
ENV ANDROID_HOME /opt/android-sdk
RUN mkdir -p ${ANDROID_HOME} && cd ${ANDROID_HOME}
ADD https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip /opt/android-sdk/
RUN unzip /opt/android-sdk/sdk-tools-linux-4333796.zip -d /opt/android-sdk
RUN rm /opt/android-sdk/sdk-tools-linux-4333796.zip

ENV PATH $ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH

RUN yes | $ANDROID_HOME/tools/bin/sdkmanager --proxy=http --proxy_host=<host> --proxy_port=<port> --licenses || true
```
