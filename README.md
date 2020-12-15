# 1 关于该仓库
 
一个简易的`Java`计算器，使用`AWT+Swing`实现，支持鼠标以及键盘输入。

![](https://img-blog.csdnimg.cn/20201216012859251.gif)

![](https://img-blog.csdnimg.cn/20201216012913957.gif)

可以配合博客使用，`CSDN`[戳这里](https://blog.csdn.net/qq_27525611/article/details/111244344)，`Github`[戳这里](https://www.bingling.site/post/java-shi-xian-jian-dan-ji-suan-qi-1/)。

# 2 运行
## 2.1 `Windows`

### 2.1.1 通过`release`下的EXE运行（建议）

### 2.1.2 通过`IDE`运行
由于只有一个源代码文件，直接在`Eclipse`或者`IDEA`中新建一个`Java`项目，直接把源码`Calculator.java`复制即可编译运行。

### 2.1.3 编译运行

使用`cmd`：
```bash
javac -encoding utf8 Calculator.java
java -Dfile.encoding=utf-8 Calculator

# JDK11
# java -Dfile.encoding=utf-8 Calculator.java
```

使用`Powershell`：
```
javac -encoding utf8 Calculator.java
java '-Dfile.encoding=utf-8' Calculator

# JDK11
# java '-Dfile.encoding=utf-8' Calculator.java
```

## 2.2 `Linux`

```bash
javac -encoding utf8 Calculator.java
java -Dfile.encoding=utf-8 Calculator

# JDK11
# java -Dfile.encoding=utf-8 Calculator.java
```

# 3 其他
觉得有用的可以给个`Star`。

有问题可以在`issue`反映。

非常感谢您的反馈。

# 4 更新日志
# 2020.12.16 
- 更换分支`main`
- 添加`Esc`以及退格
- 添加按键按下的背景颜色变化监听
- 图标修改为`Base64`
- 修复`MouseListner`问题

# 2020.07.03 
- 修复键盘回车键重复提示
- 兼容JDK8，去掉`String`的`isBlank()`方法
- 优化输出格式
- 反射优化代码
- 优化UI
- 其他若干
- 更新文档

# 2020.06.17 
- 修复空字符串问题
- 更新文档

# 2020.06.16 
- ~~打包JAR与EXE~~

# 2020.06.15
- 合并为单文件
- 更新文档
