# GradientView
Views hava a GradientDrawable Background.

给几个常用的控件添加了GradientDrawable作为背景，减少需要添加纯色圆角时的重复工作。

## 使用方法

xml

```
    <com.bovink.gradient.GradientTextView
        android:id="@+id/tv_hello"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="10dp"
        android:gravity="center"
        android:text="hello world"
        app:corners_radius="20dp"
        app:solid_color="#66ccff" />
```
