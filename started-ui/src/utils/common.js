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
     * @returns yyyy-mm-dd
     */
    formatDate: (date) => {
        const year = date.getFullYear().toString().padStart(4, "0");
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const day = date.getDate().toString().padStart(2, "0");
        console.log(); // 2023-02-16 08:25:05
        return `${year}-${month}-${day}`
    },
    week: (date) => {
        return "日一二三四五六".charAt(new Date(date).getDay());
    }
}

export default UTILS;