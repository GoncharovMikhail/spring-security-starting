/**
 * A class for resolving data on <pre> welcome </pre> page.
 */
export default class WelcomePageDataResolver {

    resolveUsernameForSearchingAgendas() {
        /* The input to resolve username from has a special name:
         * <pre> search-someones-agenda </pre>, so we resolve specified username by
         * taking the value of this input.
         *
         * <strong>NOTE:</strong>: convert in to lower case,
         * because all username are stored in lower case
         * and we wanna avoid possible silly exceptions */
        return $('input[name=search-someones-agenda]').val()
            .toLowerCase();
    }
}