# 서비스 테스트

안드로이드 앱을 구성하는 콤포넌트 중에 하나로 Service가 있습니다. 서비스와 통신하기 위한 방법은 binder를 직접 확장하는 방법, messenger를 통해 메시지를 주고받는 방법 그리고 aldl을 통해 명시적인 인터페이스를 정의해서 사용하는 방법이 있습니다.

바인더를 확장하는 경우 해당 어플리케이션 전용 서비스임으로 일반 메소드 유닛테스트와 다르지 않습니다. messenger의 경우 세부적인 인터페이스를 가지지 않음으로 메시지를 주고 받는 방식에 대한 테스트를 알아보겠습니다.  AIDL(Android Interface Definition Language)의 경우 인터페이스가 정의되어 있음으로 각각의 메소드에 대한 테스트  방식에 대해 알아보겠습니다.

각각의 방식에 대한 구현 방법은 아래 링크를 참조하시기 바랍니다.
[Bound services overview](https://developer.android.com/guide/components/bound-services)


## 바인더
## 메신저
## AIDL


![Service Lifecycle](https://developer.android.com/images/fundamentals/service_binding_tree_lifecycle.png)
