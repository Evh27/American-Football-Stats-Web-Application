window.onload = function() {
	$.get('upload/seasons/',
			function(seasons) {
				refreshSeasonList(seasons);
			}
		);
	
	$('#uploadform').submit(function(event) {
		event.preventDefault();
	});
	
	$(document).ajaxError(function() {
		jqResultsSelect().empty().html('<span class="error">Sorry, something went wrong. :(</span>');
	});

	$('div.spinner').ajaxSend(function() {
		$(this).addClass('show');
	});

	$('div.spinner').ajaxComplete(function() {
		$(this).removeClass('show');
	});
};

function jqSeasonSelect() {
	return $('#season');
}

function jqWeekSelect() {
	return $('#weeknum');
}

function jqResultsSelect() {
	return $('#results');
}

function uploadFile() {
	var formData = new FormData();
	var fileinput = $('#fileinput');
	formData.append('file', fileinput[0].files[0]);
	formData.append('season', jqSeasonSelect().val());
	formData.append('weeknum', jqWeekSelect().val());
	
	$.ajax({
	    url: 'upload',
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: 'POST',
	    success: function(response){
			jqResultsSelect().empty().html(response.message);
	    }
	});								
	
	return false;
}
        
function refreshSeasonList(seasons) {
	jqSeasonSelect().empty();
	for(var i = 0; i < seasons.length; i++) {
		var season = seasons[i];
		jqSeasonSelect().append( $('<option>', {value: season.seasonid }).text(season.year));
	}
	getWeeksForSeason();
}

function refreshWeekList(weeks) {
	jqWeekSelect().empty();
	for(var i = 0; i < weeks.length; i++) {
		var week = weeks[i];
		jqWeekSelect().append( $('<option>', {value: week.weeknum }).text(week.displayName));	
	}
}

function getWeeksForSeason() {
	var season = jqSeasonSelect().val();
	$.get('upload/seasons/' + season, 
		function(weeks) {
			refreshWeekList(weeks);
		}
	);
}

function addSeason() {
	var newSeason = $('#newSeason').val();
	var newID = jqSeasonSelect().children().size();
	$.post('upload/seasons/',
		{"newID" : newID ,"season" : newSeason},
		function(seasons) {
			refreshSeasonList(seasons);
		}
	);
}

function deleteSeason() {
	var season = jqSeasonSelect().val();
	
	$.ajax({
	    url: 'upload/seasons/' + season,
	    type: 'DELETE',
	    success: function(seasons){
			refreshSeasonList(seasons);
			getWeeksForSeason();
	    }
	});	
}

function addWeek() {
	var newWeeknum = $('#newWeeknum').val(),
		newWeekOpponent = $('#newWeekOpponent').val(),
		isHome = $('#isHome').prop('checked'),
		seasonid = jqSeasonSelect().val();
	
	$.post('upload/seasons/' + seasonid + '/week',
			{"weeknum" : newWeeknum,
			"opponent" : newWeekOpponent,
			"isHome" : isHome,
			"seasonid" : seasonid},
			function(weeks) {
				refreshWeekList(weeks);
			}
		);
}

function deleteWeek() {
	var weeknum = jqWeekSelect().val();
	var seasonid = jqSeasonSelect().val();
	
	$.ajax({
	    url: 'upload/seasons/' + seasonid + '/week/' + weeknum,
	    type: 'DELETE',
	    success: function(weeks){
	    	refreshWeekList(weeks);
	    }
	});
}
