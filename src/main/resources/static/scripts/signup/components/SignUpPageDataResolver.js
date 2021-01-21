import RegistrationData from "../entity/RegistrationData.js";

/**
 * A class for resolving data on <pre> success </pre> page.
 */
export default class SignUpPageDataResolver {

    resolveRegistrationData() {
        /* Inputs to resolve
         * <pre> email </pre>, <pre> username </pre> and <pre> password <pre> from
         * has a special <pre> ids </pre>:
         * <pre> email </pre>, <pre> username </pre> and <pre> password <pre>,
         * so I resolve specified data by taking these inputs values -
         * for each of these inputs
         * I find <pre> DOM </pre< element of the page, having special <pre> id </pre>
         * by query selector like <pre> $('#someId') </pre>, then,
         * take a value of element I just found. */
        return new RegistrationData(
            $('#email').val().toLowerCase(),
            $('#username').val().toLowerCase(),
            $('#password').val()
        );
    }
}