package com.github.captcha4j.core;

import java.io.ByteArrayOutputStream;

public interface Captcha {

    String getAnswer();

    ByteArrayOutputStream getData();

    String getDataType();

}
