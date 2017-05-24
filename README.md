<!--  

<br> 
<br> 
<div align="center"><img src="resources/trianglify-logo-with-text-close-fit.png" data-canonical-src="trianglify-logo-180.png" width="154" height="154" /></div>
<br> 
<br>

-->

# Trianglify

<!-- [![Build Status](https://travis-ci.com/sdsmdg/trianglify.svg?token=tRURwj39jsSs5JWUTxs6&branch=develop)](https://travis-ci.com/sdsmdg/trianglify) -->
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16s)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![by-SDS-MDG](https://img.shields.io/badge/by-SDS%2C%20MDG-blue.svg)](https://mdg.sdslabs.co)

<img src="resources/splash.png" data-canonical-src="resources/default_pattern_explained.jpg"/>

Trianglify is an Android library that helps creates views with beautiful patterns. Trianglify is based on MVP architecture and licensed under MIT license.

# Usages

Include following line in the gradle script of your application to include latest release of Trianglify:
```gradle
compile 'com.sdsmdg.kd:trianglify:0.9-beta'
```

# Latest Release
* [Version 0.9 beta](https://bintray.com/suyashmahar/trianglify/trianglify/0.9-beta)
 | [demo apk](https://drive.google.com/open?id=0Bz_2jvdEtUlrWEpxQ2Y2RnJGc1U)
    * Added custom palette demonstration to demo app
    * Added method `fillViewCompletely` to check if view is incompletely filled 
    * Fixes many bugs [#23](https://github.com/sdsmdg/trianglify/issues/23) [#33](https://github.com/sdsmdg/trianglify/issues/33) [#34](https://github.com/sdsmdg/trianglify/issues/34) [#35](https://github.com/sdsmdg/trianglify/issues/35) [#36](https://github.com/sdsmdg/trianglify/issues/36)

For more check complete [change log](/CHANGELOG.md).  

# Usages and Documentation
For details on usage and documentation of Trianglify, check documentation [here](DOCUMENTATION.md).

# Credits
Trianglify is inspired from qrohlf's work to generate triangle art, [Trianglify](https://github.com/qrohlf/trianglify)  

Development of Trianglify wouldn't have been possible without following libraries:
* [Delaunay Triangulator by jdiemke](https://github.com/jdiemke/delaunay-triangulator)
* [Color Picker by QuadFlask](https://github.com/QuadFlask/colorpicker)


# Guidelines for Contributors
If you want to contribute to improve this library, please read [our guidelines](CONTRIBUTING.md).

# License
Trianglify is licensed under `MIT license`. View license [here](LICENSE.md).
