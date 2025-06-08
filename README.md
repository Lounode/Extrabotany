![](web/img/logo.png)
# 额外植物学：重燃

## [English](README_en.md)
[![Crowdin](https://badges.crowdin.net/extrabotany/localized.svg)](https://crowdin.com/project/extrabotany)

曾经热门的额外植物学Mod在1.20.1回来了！

全新的版本，全新的材质，不变的是原来的味道，以及对植物魔法的热爱。

这是一个全新的植物魔法Addon。

![](web/img/overview.png)
![](web/img/overview2.png)

## 有什么更新？

### 材质

- 重绘了大部分的材质以适应高版本，更加原版风格。

### 功能花

- 商友兰：让一定范围内的村民交易打折。
- 伐木花：消耗魔力砍伐掉一大片区域里的原木。
- 雷卡兰：承受雷电的力量来生产魔力。

### 实用物品
- 魔力池充能基座：让你不再担忧把物品遗忘在魔力池里充能。
- 大师魔力之戒指：更大的魔力戒指容量——`Long.MAX`。
- 王者圣剑：射出具有追踪怪物能力的魔力光束。
- 虚空万藏：能够模拟其他遗物。
- 以及更多遗物……
### Boss
- 盖亚守护者 III
- 空之律者（未完成）

以及：**Forge和Fabric均可以游玩！！！**

## KubeJS

您可以使用KubeJS修改额外植物学的配方。

更多内容请参考：[这里](web/kubejs.md)

## Maven
对于开发者，以下是依赖额外植物学开发的一个简单教程。

由于额外植物学是个多平台项目，您可能只需要开发Forge侧相关的附属，所以以下的教程专为Forge侧设计。

以下是一个关于Forge侧依赖额外植物学的示例，将以下内容添加到您的 `build.gradle`
```groovy
repositories {
    maven {
        name = "lounode" //Extrabotany
        url = "https://maven.lounode.top/releases"
    }
}

dependencies {
    compileOnly fg.deobf("io.github.lounode.extrabotany:extrabotany-forge:[VERSION]")
    runtimeOnly fg.deobf("io.github.lounode.extrabotany:extrabotany-forge:[VERSION]")
}
```
中间您需要填写的版本号可以在[此处](https://maven.lounode.top/#/releases/io/github/lounode/extrabotany/extrabotany-forge)找到。

