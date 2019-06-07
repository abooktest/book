
# code style

java 언어의 문법의 경우 대괄호를 붙이는 방식, 코드 커멘트를 하는 방식, 멤버 변수 명명 규칙등에 대해서도 상당한 자유도가 존재합니다.
특히 팀 프로젝트의 경우라면 이런 개인간의 스타일 차이는 있을 수 밖에 없고, 
처음에 프로젝트의 코드 스타일 표준을 정하는 것이 좋은 프랙티스라고 여겨지고 있습니다.

오라클의 왜 코드 스타일이 중요한가에 따르면 아래의 이유를 나열하고 있습니다.

- 코드 비용의  80% 는 유지 보수 단계에서 발생합니다.
- 코드는 대부분 코드의 원저자가 아닌 사람에 의해서 유지 보수 됩니다.
- 코드 스타일은 가독성을 좋게 해서, 코드를 쉽게 이해할 수 있게 합니다.
- 코드를 배포한다면, 가능한 깔끔한 코드를 관리하는것이 좋습니다.

대표적인 코드 스타일로서는 google java style(https://google.github.io/styleguide/javaguide.html) 과 sun code style 등이 있습니다.

code style또는 coding style, coding convention이라고 불리기도 합니다. 대표적인 java언어의 코딩 컨벤션은  몇가지에 대해서 살펴보고 이를 툴로 강제하는 방법에 대해서 얘기하겠습니다.  

# checkstyle plugin 
AndroidStudio에도 나름의 코드 스타일 로직이 들어가 있지만 이것은 UI상으로 존재하는 로직이기에 gradle 빌드 등에서는 체크가 되지 않습니다. 그래서 사용하는 것이 checkstyle이라는 툴인데,  gradle 빌드시 마다 모든 소스코드의 스타일을 체크하게 만들 수 있습니다. 기존 gradle build에 연동하려면 아래처럼 build.gradle에 추가하면 됩니다. 여기서는 checkstyle 8.17버전을 사용했습니다.


```java
apply plugin: 'checkstyle'


checkstyle {
    toolVersion '8.17'
	configFile = rootProject.file("formatter/checkstyle.xml")
}

task checkstyle(type: Checkstyle) {
    source 'src/main'
    include '**/*.java'
    exclude '**/gen/**'
    exclude '**/R.java'
    exclude '**/BuildConfig.java'
    //ignoreFailures = true
    showViolations true
    classpath = files()
}
```

checkstyle 블럭의 옵션으로는 다음과 같은 것들이 있습니다.

property	| 설명	| 예시 
--- | --- | ---
toolVersion | check style plugin의 버전을 설정할 수 있습니다. | 	toolVersion = '8.17' 
configFile |	checkstyle 설정 파일의 위치를 지정할 수 있습니다.|	configFile = rootProject.file("formatter/checkstyle.xml"): project root 폴더의 formatter/checkstyle.xml 파일을 사용
showViolations|빌드시 위반 사항을 출력할 것인지에 대한 여부(true/fasle)를 지정할 수 있습니다.|	showViolations = true
ignoreFailures	|checksylte이 실패하더라도 빌드를 계속 하게 지정할 수 있습니다. default값은 false입니다.|	ignoreFailures = true


checkstyle task의 옵션으로는 다음의 것들이 있습니다.

- include	스타일 체크를 추가할 파일의 패턴을 정의합니다.	
- exclude	스타일 체크를 제외할 파일의 패턴을 정의합니다.	
- showViolations	앞의 설명과 동일합니다. 서브 프로젝트별로 다른 값을 지정할 수 있습니다.	
- ignoreFailures	상동.	


check style의 설정 파일은 다음과 같은 구조를 가지고 있습니다. 

```
<module name="Checker">
  <property name="charset" value="UTF-8"/>
  <property name="fileExtensions" value="java, xml, properties"/>
  <module name="JavadocPackage"/>
  <module name="TreeWalker">
    <module name="AvoidStarImport"/>
    <module name="ConstantName"/>
    <module name="EmptyBlock"/>
  </module>
</module>
```

Checker module은 모든 모듈의 부모 모듈이 되고, 모든 하위 모듈에 적용할 여러 property들을 가지고 있습니다. 

name |설명
--|--
charset	|파일의 character set을 설정합니다. 보통은 utf-8을 사용합니다.
severity|	심각성 레벨(ignore, info, warning, error)을 설정할 수 있습니다. 각 서브 모듈에서 이 설정을 덮어쓸 수 있습니다. error가 디폴트 값입니다. error로 설정하면 checkstyle 빌드가 실패하면 멈추게 됩니다.
fileExtensions|	스타일 체크를 할 파일 확장자를 설정합니다. 모든 파일이 디폴트 값입니다.



TreeWalker module은 각 Java파일별로 적용되는 것이고 여러가지 서브 모듈들을 가지고 있습니다. module은 실제로 스타일을 체크할 룰을 가지고 있는 것이라 생각하면 되고 property로 변경가능한 값을 지정할 수 있습니다. 

예로, 위의 AvoidStartImport module은 java import시에 import java.util.* 을 사용지 이것을 캐치할 수 있는 모듈이고, ConstantName은 함수명에 대한 룰을 지정할 수 있는 모듈이 되겠습니다. 



checkstyle 실행해보기 
자 이제 대략의 checkstyle설정을 마쳤다면 실행해보자.

./gradlew checkstyle
이렇게 실행하면 아래와 같은 스타일 에러를 체크해준다. 

```
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:30: 'method def rcurly' has incorrect indentation level 4, expected level should be 2. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:32: 'method def modifier' has incorrect indentation level 4, expected level should be 2. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:35: 'method def' child has incorrect indentation level 8, expected level should be 4. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:36: 'method def' child has incorrect indentation level 8, expected level should be 4. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:37: 'method def rcurly' has incorrect indentation level 4, expected level should be 2. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:39: 'method def modifier' has incorrect indentation level 4, expected level should be 2. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:44: 'method def' child has incorrect indentation level 8, expected level should be 4. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:47: 'if' has incorrect indentation level 8, expected level should be 4. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:48: 'if' child has incorrect indentation level 12, expected level should be 6. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:49: 'if rcurly' has incorrect indentation level 8, expected level should be 4. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:51: 'method def' child has incorrect indentation level 8, expected level should be 4. [Indentation]
[ant:checkstyle] [WARN] /home-mc/darren.ha/project/android-testing/app/src/main/java/com/book/checkstyle/MainActivity.java:52: 'method def rcurly' has incorrect indentation level 4, expected level should be 2. [Indentation]
```

만약 checkstyle이 에러를 낼때는 아래의 명령으로 콜 스택을 보면 힌트를 얻을 수 있다. 대부분의 경우 해당 버전의 checkstyle이 지원하지않는 module을 사용한 경우가 많다.

> ./gradlew checksylte --stacktrace


# checkstyle module
많은 checkstyle모듈들이 있지만 그것들을 다 살펴 볼 수는 없고 몇가지 자주 사용하는 것들에 대해서 소개하려고 합니다. 여기에 소개된 모듈 외에도 많은 모듈이 있고 그것을 이용해서  다양하게 코드 스타일을 만들 수 있습니다. 전체 모듈에 대한 소개는 check style의 공식 홈페이지인  http://checkstyle.sourceforge.net/checks.html 에서 확인을 하시면 됩니다. 

## 들여쓰기(Indentation)

```
<module name="Indentation">
  <property name="basicOffset" value="4"/>
  <property name="throwIndent" value="8"/>
</module>
```
들여쓰기에 관한 것을 설정을 할 수 있습니다. 다음과 같은 설정값이 있습니다.

properties name|	description
--|--
basicOffset|	들여쓰기 레벨별로 얼마나 들여쓰기를 할것인지를 설정할 수 있습니다.기본값은 4입니다.
throwIndent|	throw 문의 경우 얼마나 들여쓰기를 할 것인지를 설정할 수 있습니다. 기본값은 4입니다.
lineWrappingIndentation|	라인의 길이가 길어져서 분리할때 사용할 들여쓰기를 설정 합니다. 기본값은 4 입니다.

## 라인 길이(LineLength)
라인의 길이를 체크하는 모듈이고, 라인의 길이가 너무 길어지게 되면 가독성에 영향을 주기 때문에 보통 팀별로 적당한 값을 정합니다. 아래와 같은 설정 값이 있습니다.

설정값|	설명
--|--
ignorePattern|	라인 길이를 체크하지 않는 라인에 대한 regex를 설정할 수 있습니다.
max|	라인의 최대길이를 설정합니다. 디폴트 값은 80 입니다.

아래 설정은 한 라인의 길이를 100으로 설정하고, import 문, 각종 url이 들어가는 라인에 대해서는 라인 길이 체크를 하지 않게 하는 설정의 예입니다.

```
<module name="LineLength">
	<property name="max" value="100"/>
	<property name="ignorePattern" value="^package.*|^import.*|a href|href|http://|https://|ftp://"/>
</module>
```

## 제어자 순서(ModifierOrder)
private, final, static등의 제어자 순서를 체크하는 모듈입니다. 제어자는 아래의 순서대로 우선순위가 높습니다.

1. public
1. protected
1. private
1. abstract
1. default
1. static
1. final
1. transient
1. volatile
1. synchronized
1. native
1. strictfp

```java
class Example {
	static public COUNT = 5; // 제어자 순서에 따르면 public이 먼저 선언되어야 합니다.
	final public void doSomething() {  //제어자 순선에 따르면 public final void 가 되어야 합니다.
	}
}
```


## 멤버 변수 규칙(MemberName)
멤버 변수에 특정한 규칙을 준수하는지 체크하는 모듈입니다. 아래의 설정값들을 가지고 있습니다. 

설정값	|설명
--|--
format|	변수명 규칙을 정규표현식으로 표현합니다. 초기값은 "^[a-z][a-zA-Z0-9]*$" 입니다. 
applyToPublic|	public 멤버 변수에 적용할지의 여부를 설정합니다. 초기값은 true입니다.
applyToProtected|	protected 멤버 변수에 적용할지의 여부를 설정합니다. 초기값은 true입니다.
applyToPrivate|	private 멤버 변수에 적용할지의 여부를 설정합니다. 초기값은 true입니다.
applyToPackage|	package 멤버 변수에 적용할지의 여부를 설정합니다. 초기값은 true입니다.


아래 예제는 m으로 시작하고 알바벳이나 숫자로 시작하되, m다음 문자는 대문자여야 함을 기술하는 멤버 변수 규칙이다. 즉, mVariable은 통과하지만, mvarialbe은 체커에 통과하지 못한다.

```
<module name="MemberName">
  <property name="format" value="^m[A-Z][a-zA-Z0-9]*$"/>
</module>
```

## .* import 금지(AvoidStarImport)
AndroidStudio에서 같은 패키지의 import구문이 3개 이상 중복될때에는 자동으로 xx.* 로 import 하도록하는 규칙이 있습니다. 이것을 star import라고 부릅니다.  여러개 패키지가 이런 식으로 star import를 하게 되면 같은 패키지명이 충돌이 나서 알아보기 힘든 에러를 내는 경우가 종종 있습니다. 이럴때 사용하면 좋은 모듈입니다. 

아래의 설정값들을 가지고 있습니다. 

설정값|	설명
--|--
excludes|	star import를 허용하는 패키지 리시트를 설정합니다. 초기값은 비어 있습니다.
allowClassImports|	클래스의 star import를 허용할지 여부를 설정합니다. 초기값은 false입니다.
allowStaticMemberImports|	static 클리스의 star import를 허용할지 여부를 설정합니다. 초기값은 false


아래 예제는 java.io, java.net, java.lang.Math 만 star import를 허용하고 그외의 패키지에는 허용하지 않게 설정한 예입니다.

```
<module name="AvoidStarImport">
  <property name="excludes" value="java.io,java.net,java.lang.Math"/>
  <property name="allowClassImports" value="false"/>
  <property name="allowStaticMemberImports" value="false"/>
</module>
```


# checkstyle을 AndroidStudio에 적용하기
프로젝트에 checkstyle 설정을 했다고 해서 Android Studio의 formatter설정을 같이 바꿔주지는 않습니다. 그래서 Android Studio의 formatting 명령을 사용하면 애써 맞춰놓은 코드 스타일을 다시 맞춰야 하는 경우가 생길 수 있습니다. 그래서 checkstyle의 설정을 Android Studio의 formatter에 적용하는 작업이 필요합니다. 

우선 Android Studio에 'checkstyle' plugin설치가 필요 합니다. 설치 및 스타일 import 방법은 아래와 같습니다.

1. Android Studio에서  File>Settings>Plugins 탭에서 'Browser Repositories'버튼을 선택합니다.
1. checkstyle로 검색하여 'checkstyle-IDEA` 플로그인을 선택하여 설치합니다.

MDEP > checkstyle > checkstyle-plugin.PNG
1. 설치후 Android Studio를 닫고 다시 실행합니다.
1. 다시 File> Settings> Editor> code style> Java 를 선택합니다.
1. scheme> 설정> import scheme> checkstyle configuration 에서 checkstyle설정 파일을 선택합니다.

MDEP > checkstyle > checkstyle-import.PNG
1. 완료.

# 특정 파일 제외하기
프로젝트를 진행하다보면 특정 부분에 부득이한 사정으로 check style에러를 허용해줘야 할 경우가 생길 수 있습니다. 예를 들어 멤버 변수에 m prefix를 붙이기로 했는데, 서버와의 통신에서 사용하는 data 클래스는 m prefix가 붙지 않을 경우 이 데이터 클래스에는 checkstyle예외를 설정할 수 있어야 합니다. 이런 경우 어떻게 checkstyle 체크를 하지 않도록 설정할 수 있는지에 대해서 알아보려고 합니다. 

## gradle을 통해서 제외 하기
앞서 보았던 build.gradle에 보면 exclude 옵션이 있었습니다. 이것을 이용해서 제외하면 비교적 간단하게 특정파일의 checkstyle체크를 제외할 수 있습니다. 반면에 매번 gradle.build를 수정해야 하므로 약간의 번거러움이 있겠습니다.

아래의 예와 같이 설정하면,  MyData.java라는 클래스는 checksylte 체크를 하지 않게 됩니다.

task checkstyle(type: Checkstyle) {
    source 'src/main/java'
    include '**/*.java'
    exclude '**/MyData.java'
    classpath = files()
}


## module을 통해서 제외하기
다른 방법으로는 checkstyle의 모듈을 통해서 제외되게 할수도 있습니다. 이것은 소스 코드에 특정 패턴의 주석을 적음으로서 checkstyle을 켜거나 끄거나 할 수 있습니다.  이렇게 하려면 다음의 모듈을 TreeWalker 하위에 추가하면 됩니다.  

```
<module name="SuppressionCommentFilter"/>
```

이 모듈을 설정하면 off 커멘트와 ON커멘트 사이에 있는 라인은 체크 하지 않게 된다. 즉 아래의 예제에서는 멤버 변수가 m prefix가 안붙어서 에러가 나야 하는데 이것을 건너 뛰게 됩니다.

import lombok.Data;

```java
// CHECKSTYLE:OFF
@Data
public class MyData {
    private String name;
    private int age;
}
// CHECKSTYLE:ON
```


