export function decodeToken(token) {
  try {
    const payload = token.split('.')[1]
    // JWT uses base64url → convert to standard base64
    let base64 = payload.replace(/-/g, '+').replace(/_/g, '/')
    while (base64.length % 4) base64 += '='

    // atob gives Latin-1 bytes, convert to UTF-8 properly
    const binaryStr = atob(base64)
    const bytes = new Uint8Array(binaryStr.length)
    for (let i = 0; i < binaryStr.length; i++) {
      bytes[i] = binaryStr.charCodeAt(i)
    }
    const json = new TextDecoder().decode(bytes)
    return JSON.parse(json)
  } catch (e) {
    console.warn('JWT decode failed:', e)
    return null
  }
}

export function isTokenExpired(token) {
  if (!token) return true
  const decoded = decodeToken(token)
  if (!decoded) return false // if we can't decode, assume it's valid (server will reject)
  return decoded.exp * 1000 < Date.now()
}
