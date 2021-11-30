package com.github.captcha4j.core.image.renderer;

import java.awt.*;
import java.util.Random;

public enum EmbeddedFont {


    CALLING_CODE("/fonts/calling_code/CallingCode-Regular.ttf", Font.TRUETYPE_FONT),
    OPEN_DYSLEXIC("/fonts/open_dyslexic/OpenDyslexic-Regular.otf", Font.TRUETYPE_FONT),
    NANDIA("/fonts/nandia/Nandia.ttf", Font.TRUETYPE_FONT),
    RA_MONO("/fonts/ra_mono/Ra-Mono.otf", Font.TRUETYPE_FONT),
    RAINY_HEARTS("/fonts/rainyhearts/rainyhearts.ttf", Font.TRUETYPE_FONT),
    SKETCHY_TIMES("/fonts/sketchy_times/sketchytimes.ttf", Font.TRUETYPE_FONT),
    SUPER_BOLD("/fonts/super_bold/super_bold.ttf", Font.TRUETYPE_FONT),
    VCR_OSD_MONO("/fonts/vcr_osd_mono/VCR_OSD_MONO.ttf", Font.TRUETYPE_FONT),
    VERILY("/fonts/verily_serif_mono/VerilySerifMono.otf", Font.TRUETYPE_FONT),
    WHITE_RABBIT("/fonts/white_rabbit/whitrabt.ttf", Font.TRUETYPE_FONT);

    private static final Random r = new Random(System.currentTimeMillis());

    private String filename;
    private int type;

    EmbeddedFont(String filename, int type){
        this.filename = filename;
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public int getType() {
        return type;
    }

    public static EmbeddedFont random(){
        int index = r.nextInt(EmbeddedFont.values().length);
        return EmbeddedFont.values()[index];
    }

}
