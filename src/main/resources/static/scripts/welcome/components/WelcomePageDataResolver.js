export default class WelcomePageDataResolver {

    resolveUsernameForSearchingAgendas() {
        return $('input[name=search-someones-agenda]').val().toLowerCase();
    }
}