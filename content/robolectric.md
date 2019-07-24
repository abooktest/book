# 소개
Robolectric는 Android 개발에 있어 TDD를 쉽게 하도록 도와주는 테스트 프레임웍입니다.
따라서 단말이나 에뮬레이터에서 수행해야 하는 무거운 테스트를 PC의 JVM에서 빠르게 테스트 할 수 있도록 Android SDK Mocking을 지원합니다.

현재 최신 버전은 4.0 입니다.
>#### [Robolectric 4.0 Released!](http://robolectric.org/blog/2018/10/25/robolectric-4-0/)
>###### OCT 25, 2018  •  JONATHAN GERRISH, BRETT CHABOT, AND CHRISTIAN WILLIAMS  <br> Robolectric 4.0 is released! Here’s what’s new!

18’Google I/O에서 발표한 Jetpack 구성 모듈 중 하나인 AndroidX Test는 위 Robolectric v4.0과 공통 테스트 API를 제공합니다. AndroidX Test는 기존의 Android JUnit 4 지원 기능, Espresso 뷰 상호 작용 라이브러리, 여러 가지 새로운 주요 테스트 API를 포함하고 이러한 API는 실제 기기와 가상 기기에서 instrumentation 테스트에 사용할 수 있는데 Robolectric 4.0에서는 이들 API를 로컬 JVM 테스트에도 사용할 수 있도록 지원하고 있습니다.


#### A Robolectric 3.x style test:
```java
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
```java
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

# 설정
 
 ~~ ### Bazel ~~

### Gradle
#### build.gradle 수정
