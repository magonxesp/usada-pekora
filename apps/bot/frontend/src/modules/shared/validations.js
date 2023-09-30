
/**
 * Check if is Regex
 * 
 * @param {any} value 
 * @returns {boolean}
 */
export function isRegex(value) {
  if (typeof value != 'string' || value == '') {
    return false
  }

  try {
    new RegExp(value)
  } catch (exception) {
    return false
  }

  return true
}

/**
 * Check if is the value is not empty
 * 
 * @param {any} value 
 * @returns {boolean}
 */
export function isNotEmpty(value) {
  if (typeof value === 'undefined' || value === null) {
    return false
  }

  if (typeof value === 'string' && value === '') {
    return false
  }

  if (Array.isArray(value) && value.length == 0) {
    return false
  }

  return true
}
