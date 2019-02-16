$(document).ready(function () {
    $("#TableUser #checkall").click(function () {
        if ($("#TableUser #checkall").is(':checked')) {
            $("#TableUser input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    $("[data-toggle=tooltip]").tooltip();
});

