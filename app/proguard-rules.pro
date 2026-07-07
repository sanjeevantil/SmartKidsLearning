# Smart Kids Learning ProGuard Rules
-keepattributes *Annotation*
-keep class com.smartkids.learning.data.local.database.entity.** { *; }
-keep class com.smartkids.learning.data.local.database.dao.** { *; }
-dontwarn kotlinx.coroutines.flow.*
-keepclassmembers class kotlinx.coroutines.flow.** { *; }
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }