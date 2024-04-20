const UTILS = {
    GET: (k) => {
        for (let _ of location.search.substring(1).split("&")) {
            _ = _.split('=')
            if (k === _[0]) {
                return _[1]
            }
        }
    },
    /**
     *
     * @param date Date
     * @returns String
     */
    formatDate: (date) => {
        return date.toLocaleDateString().replaceAll('/', '-')
    },
    week: (date) => {
        return "日一二三四五六".charAt(new Date(date).getDay());
    }
}

export default UTILS;