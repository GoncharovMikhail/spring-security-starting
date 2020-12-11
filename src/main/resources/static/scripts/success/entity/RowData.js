/** A wrapper class, containing private fields:
 * <ul>
 *     <li>
 *         day
 *     </li>
 *     <li>
 *         time
 *     </li>
 *     <li>
 *         note
 *     </li>
 *     <li>
 *         accessible
 *     </li>
 * </ul>
 */
export default class RowData {

    #day;

    #time;

    #note;

    #accessible;

    constructor(day, time, note, accessible) {
        this.#day = day;
        this.#time = time;
        this.#note = note;
        this.#accessible = accessible;
    }

    getDay() {
        return this.#day;
    }

    getTime() {
        return this.#time;
    }

    getNote() {
        return this.#note;
    }

    isAccessible() {
        return this.#accessible;
    }

    equals(otherData) {
        if (!(otherData instanceof RowData)) {
            return false;
        } else {
            if (this.getDay() === otherData.getDay() &&
                this.getTime() === otherData.getTime() &&
                this.getNote() === otherData.getNote() &&
                this.isAccessible() === otherData.isAccessible()) {
                return true;
            }
        }
    }
}