const UTILS = {
  GET: (k) => {
    for (let _ of location.search.substring(1).split("&")) {
      _ = _.split('=')
      if (k === _[0]) {
        return _[1]
      }
    }
  },
}

export default UTILS;