
$(document).ready(function() {
    $("#refresh").on('click',refreshChallenge);
    $("#audio_language").on('change', loadAudioCaptcha);
    $("#verify").on('click', verify);
    refreshChallenge();
    $("#modal").modal("show");
});

let captchaDetails = {};

refreshChallenge = function(){
    $.ajax({
        url: "/captcha/challenge",
        method: "GET",
        success: function (data, status, xhr) {
            clearTimeout(captchaDetails.timer)
            captchaDetails.challenge = xhr.getResponseHeader("X-Captcha-Challenge");
            captchaDetails.timeoutSeconds = xhr.getResponseHeader("X-Captcha-Timeout");
            captchaDetails.validUntil = xhr.getResponseHeader("X-Captcha-ValidUntil");

            updateTimeout();
            loadImageCaptcha();
            loadAudioCaptcha();
            $("#answer").val("");
        }
    });
};

loadImageCaptcha = function(){
    $.ajax({
        url: "/captcha/image",
        method: "GET",
        headers : { "X-Captcha-Challenge" : captchaDetails.challenge },
        xhrFields: { responseType: "blob" },
        success: function (data) {
            $("#image_challenge").attr("src", window.URL.createObjectURL(data));
        }
    });
};

loadAudioCaptcha = function(){
    let lang = $("#audio_language").val();
    $.ajax({
        url: "/captcha/audio",
        method: "GET",
        headers : { "X-Captcha-Challenge" : captchaDetails.challenge },
        xhrFields: { responseType: "blob" },
        data: { language: lang },
        success: function (data) {
            $("#audio_challenge").attr("src", window.URL.createObjectURL(data));
        }
    });
};

verify = function(){
    let answer = $("#answer").val();
    $.ajax({
        url: "/captcha/verify",
        method: "POST",
        headers : { "X-Captcha-Challenge" : captchaDetails.challenge },
        data: { answer: answer },
        success: function (result) {
            if(result.valid){
                alert("Solved");
            }else{
                alert("Nope");
            }
        }
    });
};

updateTimeout = function(){
    if(captchaDetails.timeoutSeconds >= 0){
        $("#timeout").text(captchaDetails.timeoutSeconds);
        captchaDetails.timeoutSeconds -= 1;
        captchaDetails.timer = setTimeout(updateTimeout, 1000);
    } else {
        console.log("Captcha timeout expired... refreshing.");
        refreshChallenge();
    }
};
