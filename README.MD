
# RSA-android
Suitable for android of the RSA tool.

1. Initialize the PublicKey or PrivateKey method.
2. Encrypt and decrypt rsa data.
3. Compatible with openSSL.
4. Compatible with android 2.3+.

[![](https://jitpack.io/v/itvincent-git/RSA-android.svg)](https://jitpack.io/#itvincent-git/RSA-android)

## Sample
```
InputStream inputStream = getBaseContext().getResources().openRawResource(R.raw.publickey);
String string = convertStreamToString(inputStream);
mPublicKey = RSAAndroid.openSSLPemStringToPublicKey(string);
Log.i(TAG, "public key init done");

...

byte[] result = RSAAndroid.encode(raw.getBytes(), mPublicKey);
Log.i(TAG, "encode data done");                    
```

## Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## Step 2. Add the dependency
```
dependencies {
    compile 'com.github.itvincent-git:RSA-android:1.0.1'
}
```