(function ($) {
    $(function () {

        $('.button-collapse').sideNav();
        $('.parallax').parallax();
        $('.dropdown-button').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrain_width: false, // Does not change width of dropdown to that of the activator
            hover: false, // Activate on hover
            gutter: 0, // Spacing from edge
            belowOrigin: false // Displays dropdown below the button
        }
        );
        $('.collapsible').collapsible();
        $('ul.tabs').tabs();
        $(document).ready(function () {
            $('select').material_select();
        });



    }); // end of document ready
})(jQuery); // end of jQuery name space