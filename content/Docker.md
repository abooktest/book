# Docker

응용 프로그램들을 소프트웨어 컨테이너 안에 배치시켜 배포 및 실행을 위한 경량화 패키지입니다.

개발환경 등의 모든 종속성을 패키지화 하여 격리된 가상공간만을 사용하기 때문에, OS 전체를 가상화 하는 방식보다 가볍고 빠르게 동작합니다.
각 Container 들은 완전히 격리된 공간을 구성하며, File System의 가상화만 이루어집니다.

# Docker Image & Docker Container
Docker Image는 소프트웨어, 라이브러리, 환경 등을 설치한 후 배포가능한 패키지를 말하며, 
Docker Container는 Docker Image를 실행 한 형태입니다. 즉 Image는 실행 할 수 있는 패키지로 볼 수 있고, Container는 실행된 각각의 프로세스로 볼 수 있습니다. 또한 하나의 Image는 여러개의 Container로 실행이 가능합니다.

# Docker Hub
Docker Image들의 저장소인 Docker Hub는 현재 많은 사람들에 의해 Image가 제작 및 공유되고 있습니다. ubuntu, gradle 등의 SW 들 또한 등록되어 있고, 이러한 Image들은 Official Image로 표기하고 있습니다. 그리고 Official Image들은 Docker 환경에 맞게 재구성하여 경량화(Ubuntu의 경우 약 30MB) 되어 있습니다. 
개인 사용자 또한  용도에 맞게 선택하여 Customizing 이 가능하며, Docker Hub의 중앙저장소에 쉽게 올릴 수 있고(push), 또 필요한 Image를 받을 수 있습니다. (pull) 또한 받은 Image를 수정하여 또 다른 Image를 생성 할 수 있으며, 이 때 생성된 Image는 Base Image 에서 변경된 부분만을 저장하게 됩니다.



-------------
내용 추가 중 Docker layer
https://medium.com/@jessgreb01/digging-into-docker-layers-c22f948ed612

-------------



# Docker 설치
