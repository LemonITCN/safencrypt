$(function () {

    $(document).ajaxStart(function (a, b, c) {
        console.log("13123gogogo = " + JSON.stringify(b));
    }).ajaxSend(function (a, b, c) {
        console.log("1b = " + JSON.stringify(b));
    });
    $("#hello").click(function () {
        $.ajax({
            url: "http://localhost:8080/abc",
            type: "POST",
            data: {
                username: "liuri"
            },
            beforeSend: function (a, b, c) {
                console.log("lll = = = " + JSON.stringify(c));
                b.data = "username=hahahah"
            },
            success: function (data) {
                console.log("data = " + data);
            }
        });
    });
});