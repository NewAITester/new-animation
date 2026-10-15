# RigToon

اپلیکیشن آفلاین‌محور اندروید برای طراحی کاراکتر دوبعدی و انیمیشن اسکلتی.

مخزن: NewAITester/new-animation

## وضعیت فعلی

فاز ۰، گام ۲: پوستهٔ اپلیکیشن، ماژول‌ها، تم تاریک، Hilt، MVI، صفحهٔ خالی
پروژه‌ها، ناوبری درباره، منابع فارسی و انگلیسی، تست‌ها و CI.

قابلیت‌های فازهای بعد هنوز پیاده‌سازی نشده‌اند. ماژول‌های آینده فقط تنظیمات
ساخت معتبر دارند و کلاس یا رفتار جای‌نگهدار ندارند.

ساخت و اجرای تست‌ها در محیط تهیهٔ کد انجام نشده است؛ قبولی CI یا اجرای
موفق دستگاهی ادعا نمی‌شود.

## پیش‌نیازها

- JDK 17
- Gradle 9.6.0
- Android SDK Platform 37
- Android SDK Build Tools 36.0.0
- دستگاه یا شبیه‌ساز API 26 یا بالاتر

ANDROID_HOME را تنظیم کنید یا sdk.dir را در local.properties قرار دهید.
local.properties نباید وارد مخزن شود.

## ساخت و بررسی

```bash
gradle --no-daemon :app:assembleDebug
gradle --no-daemon check
gradle :core:ui:testDebugUnitTest :feature:projects:testDebugUnitTest
gradle :app:assembleDebugAndroidTest :feature:projects:assembleDebugAndroidTest
```

APK در app/build/outputs/apk/debug/app-debug.apk قرار می‌گیرد.

## نصب و اجرا

```bash
gradle :app:installDebug
adb shell am start -n com.rigtoon/com.rigtoon.app.MainActivity
```

## تست دستگاهی

```bash
gradle :app:connectedDebugAndroidTest :feature:projects:connectedDebugAndroidTest
```

## قالب‌بندی و تحلیل ایستا

```bash
gradle ktlintCheck detekt
gradle ktlintFormat
```

## Wrapper رسمی

```bash
gradle wrapper --gradle-version 9.6.0 --distribution-type bin
```

Checksum رسمی توزیع را بررسی و در distributionSha256Sum ثبت کنید.
اسکریپت‌ها و JAR رسمی تولیدشده را Commit کنید. Wrapper دست‌نویس یا Checksum
بررسی‌نشده ارائه نشده است. پس از تولید Wrapper می‌توان از ./gradlew استفاده کرد.
CI فعلاً نسخهٔ ثابت Gradle را با setup-gradle نصب می‌کند.

## زبان و جهت

رابط از زبان و جهت دستگاه پیروی می‌کند. زبان‌های en و fa در locales_config
ثبت شده‌اند. انتخاب‌گر زبان داخل برنامه در فاز تنظیمات اضافه می‌شود.
فقط بوم و تایم‌لاین هنگام پیاده‌سازی خود به LTR محدود می‌شوند.

## CI

android-ci.yml روی Push و Pull Request، ساخت، تست‌های واحد، lint، detekt و
ktlint را اجرا می‌کند. instrumented-tests.yml دستی است و API 26 و 35 را
بررسی می‌کند. بررسی API 37 همچنان لازم است.

## مجوزها

JUnit 4 با EPL-1.0 فقط در وابستگی‌های تست نگه داشته شده است. این مورد استثنا
نسبت به شرط مجوز صرفاً permissive است. ممیزی کامل وابستگی‌های گذرا انجام نشده است.

## مستندات

- docs/PROGRESS.md
- docs/DECISIONS.md
- THIRD_PARTY_NOTICES.md
