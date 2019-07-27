# 소개
Robolectric는 Android 개발에 있어 TDD를 쉽게 하도록 도와주는 테스트 프레임웍입니다.
따라서 단말이나 에뮬레이터에서 수행해야 하는 무거운 테스트를 PC의 JVM에서 빠르게 테스트 할 수 있도록 Android SDK Mocking을 지원합니다.

현재 최신 버전은 4.0 입니다.
>#### [Robolectric 4.0 Released!](http://robolectric.org/blog/2018/10/25/robolectric-4-0/)
>###### OCT 25, 2018  •  JONATHAN GERRISH, BRETT CHABOT, AND CHRISTIAN WILLIAMS  <br> Robolectric 4.0 is released! Here’s what’s new!

18’Google I/O에서 발표한 Jetpack 구성 모듈 중 하나인 AndroidX Test는 위 Robolectric v4.0과 공통 테스트 API를 제공합니다. AndroidX Test는 기존의 Android JUnit 4 지원 기능, Espresso 뷰 상호 작용 라이브러리, 여러 가지 새로운 주요 테스트 API를 포함하고 이러한 API는 실제 기기와 가상 기기에서 instrumentation 테스트에 사용할 수 있는데 Robolectric 4.0에서는 이들 API를 로컬 JVM 테스트에도 사용할 수 있도록 지원하고 있습니다.


#### A Robolectric 3.x style test:
```kotlin
@RunWith(RobolectricTestRunner::class)
class RobolectricTest {
  @Test fun clickingOnTitle_shouldLaunchEditAction() {
    val activity = Robolectric.setupActivity(NoteListActivity::class.java)
    ShadowView.clickOn(activity.findViewById(R.id.title));
    assertThat(ShadowApplication.getInstance().peekNextStartedActivity().action)
            .isEqualTo("android.intent.action.EDIT")
  }
}
```

#### Robolectric 4.x/instrumentation test:
```kotlin
@RunWith(AndroidJUnit4::class)
class OnDeviceTest {
  @get:Rule val rule = ActivityTestRule(NoteListActivity::class.java)

  @Test fun clickingOnTitle_shouldLaunchEditAction() {
    onView(withId(R.id.button)).perform(click())
    intended(hasAction(equalTo("android.intent.action.EDIT")))
  }
}
```
위와같이 Robolectric는 instrumentation test의 많은 이디엄들을 지원하고 있고 향후 릴리즈에서는 `androidx.test`에 대한 지원이 더욱 더 확대될 뿐만아니라 Robolectric-originated testing paradigms이 전통적인 instrumentation test에 도입될 예정입니다.


~~### Bazel~~
~~Bazel 개발 환경을 설명해야 할까??~~

### Gradle
#### build.gradle에 아래와 같이 추가합니다.
*includeAndroidResources*  를 통해 앱에 포함되는 리소스를 테스트에서도 참조할 수 있게 됩니다.
```
android {
  testOptions {
    unitTests {
      includeAndroidResources = true
    }
  }
}

dependencies {
  //최신 버전을 넣어 줍니다.
  testImplementation 'org.robolectric:robolectric:4.3'
}
```
#### gradle.properties에 아래와 같이 추가합니다.
```
android.enableUnitTestBinaryResources=true
```

#### Test class에 아래와 같이 어노테이션을 추가합니다.
```
@RunWith(RobolectricTestRunner.class)
public class SandwichTest {
 ...
}
```

# 기본 예제
애플리케이션 환영 페이지 layout을 생성합니다. 
```xml
<!--xml version="1.0" encoding="utf-8"?-->
<linearlayout android:layout_height="match_parent" android:layout_width="match_parent" xmlns:android="http://schemas.android.com/apk/res/android"> 
    <button android:id="@+id/login" android:layout_height="wrap_content" android:layout_width="wrap_content" android:text="Login"> 
</button></linearlayout>
 ```
Click 버튼을 통해 LoginActivity가 런칭되도록 작성합니다.
```java
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
 
        final View button = findViewById(R.id.login);
        button.setON-CLICKListener(new View.ON-CLICKListener() {
            @Override
            public void ON-CLICK(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });
    }
}
```

이것을 테스트하기 위해서는 "Login"버튼이 사용자에 의해 클릭될 때 정상적으로 인텐트가 시작되어야합니다. 아래와 같이 Roblectric을 이용하여 LoginActivity가 실제 시작하지 않아도 WelcomeActivity가 올바른 intent를 발생시켰는지 테스트 할 수 있습니다.
```java
@RunWith(RobolectricTestRunner.class)
public class WelcomeActivityTest {
 
    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        WelcomeActivity activity = Robolectric.setupActivity(WelcomeActivity.class);
        activity.findViewById(R.id.login).performClick();
 
        Intent expectedIntent = new Intent(activity, WelcomeActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }
}
```
# Shadow
#### Shadowing 생성자
#### Shadow 클래스
#### Shadowing 메소드

# Configuration
#### sdk, minSdk, maxSdk properties
- SDK, Resources, Native Method에 대한 Emulation이 가능
#### manifest, resource dir, asset dir 
#### res qualifier
#### system properties
#### qualifiers
#### @Config Annotation
#### robolectic.properties File
#### Global Configuration
- final static 값도 변경 가능 - Build.VERSION 클래스 정보를 수정가능
#### Device Configuration
- http://robolectric.org/device-configuration/  

# Lifecycle
#### Activity
##### start(), pause(), resume(), stop(), destroy()  
##### save(), restore()
##### visible state

# Add-on
- http://robolectric.org/using-add-on-modules/  

# APIDocs
- http://robolectric.org/javadoc/latest/

# Best Practice
- http://robolectric.org/best-practices/
