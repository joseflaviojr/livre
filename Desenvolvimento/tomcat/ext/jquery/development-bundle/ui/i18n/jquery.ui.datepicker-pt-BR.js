/* Brazilian initialisation for the jQuery UI date picker plugin. */
/* Written by Leonildo Costa Silva (leocsilva@gmail.com). */
/* Adaptado por José Flávio de Souza Dias Júnior (contato@joseflavio.com). */
jQuery(function($){
	$.datepicker.regional['pt-BR'] = {
		closeText: "OK", // Display text for close link
		prevText: "Ante", // Display text for previous month link
		nextText: "Prox", // Display text for next month link
		currentText: "Hoje", // Display text for current month link
		monthNames: ["Janeiro","Fevereiro","Mar&ccedil;o","Abril","Maio","Junho",
			"Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"], // Names of months for drop-down and formatting
		monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"], // For formatting
		dayNames: ["Domingo", "Segunda", "Ter&ccedil;a", "Quarta", "Quinta", "Sexta", "S&aacute;bado"], // For formatting
		dayNamesShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"], // For formatting
		dayNamesMin: ["Do","Se","Te","Qa","Qi","Se","Sa"], // Column headings for days starting at Sunday
		weekHeader: "Sm", // Column header for week of the year
		dateFormat: "dd/mm/yy", // See format options on parseDate
		firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
		isRTL: false, // True if right-to-left language, false if left-to-right
		showMonthAfterYear: false, // True if the year select precedes month, false for month then year
		yearSuffix: "" // Additional text to append to the year in the month headers
	};
	$.datepicker.setDefaults($.datepicker.regional['pt-BR']);
});