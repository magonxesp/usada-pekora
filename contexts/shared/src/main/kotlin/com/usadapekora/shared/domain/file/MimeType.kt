package com.usadapekora.shared.domain.file

enum class MimeType(val extensions: Array<String>, val mimeType: String) {
    /** AbiWord document */
    APPLICATION_ABI_WORD(arrayOf(".abw"), "application/x-abiword"),

    /** Archive document (multiple files embedded) */
    APPLICATION_ARCHIVE(arrayOf(".arc"), "application/x-freearc"),

    /** Atom Documents */
    APPLICATION_ATOM_XML(arrayOf(".atom"), "application/atom+xml"),

    /** Atom Category Documents */
    APPLICATION_ATOMCAT_XML(arrayOf(".atomcat"), "application/atomcat+xml"),

    /** Amazon Kindle eBook format */
    APPLICATION_AMAZON_KINDLE(arrayOf(".azw"), "application/vnd.amazon.ebook"),

    /** Apple Installer Package */
    APPLICATION_APPLE_INSTALLER(arrayOf(".mpkg"), "application/vnd.apple.installer+xml"),

    /** C-Shell script */
    APPLICATION_CSH(arrayOf(".csh"), "application/x-csh"),

    /** ECMAScript */
    APPLICATION_ECMASCRIPT(arrayOf(".es"), "application/ecmascript"),

    /** Electronic publication (EPUB) */
    APPLICATION_EPUB(arrayOf(".epub"), "application/epub+zip"),

    /** GZip Compressed Archive */
    APPLICATION_GZIP(arrayOf(".gz"), "application/gzip"),

    /** Java Archive (JAR) */
    APPLICATION_JAVA_ARCHIVE(arrayOf(".jar"), "application/java-archive"),

    /** JavaScript */
    APPLICATION_JAVASCRIPT(arrayOf(".js"), "application/javascript"),

    /** JSON format */
    APPLICATION_JSON(arrayOf(".json"), "application/json"),

    /** JSON-LD format */
    APPLICATION_JSON_LD(arrayOf(".jsonld"), "application/ld+json"),

    /** Microsoft Excel */
    APPLICATION_MS_EXCEL(arrayOf(".xls"), "application/vnd.ms-excel"),

    /** Microsoft Excel (OpenXML) */
    APPLICATION_MS_EXCEL_OPEN_XML(arrayOf(".xlsx"), " \tapplication/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

    /** MS Embedded OpenType fonts */
    APPLICATION_MS_OPENTYPE_FONTS(arrayOf(".eot"), "application/vnd.ms-fontobject"),

    /** Microsoft PowerPoint */
    APPLICATION_MS_POWER_POINT(arrayOf(".ppt"), "application/vnd.ms-powerpoint"),

    /** Microsoft PowerPoint (OpenXML) */
    APPLICATION_MS_POWER_POINT_OPEN_XML(
        arrayOf(".pptx"),
        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
    ),

    /** Microsoft Word */
    APPLICATION_MS_WORD(arrayOf(".doc"), "application/msword"),

    /** Microsoft Word (OpenXML) */
    APPLICATION_MS_WORD_OPEN_XML(arrayOf(".docx"), "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

    /** Microsoft Visio */
    APPLICATION_MS_VISIO(arrayOf(".vsd"), "application/vnd.visio"),

    /** Any kind of binary data */
    APPLICATION_OCTET_STREAM(arrayOf(".bin"), "application/octet-stream"),

    /** OGG */
    APPLICATION_OGG(arrayOf(".ogx"), "application/ogg"),

    /** OpenDocument presentation document */
    APPLICATION_OPEN_DOCUMENT_PRESENTATION(arrayOf(".odp"), "application/vnd.oasis.opendocument.presentation"),

    /** OpenDocument spreadsheet document */
    APPLICATION_OPEN_DOCUMENT_SPREADSHEET(arrayOf(".ods"), "application/vnd.oasis.opendocument.spreadsheet"),

    /** OpenDocument text document */
    APPLICATION_OPEN_DOCUMENT_TEXT(arrayOf(".odt"), "application/vnd.oasis.opendocument.text"),

    /** Adobe Portable Document Format (PDF) */
    APPLICATION_PDF(arrayOf(".pdf"), "application/pdf"),

    /** Hypertext Preprocessor (Personal Home Page) */
    APPLICATION_PHP(arrayOf(".php"), "application/x-httpd-php"),

    /** PKCS #10 */
    APPLICATION_PKCS_10(arrayOf(".p10"), "application/pkcs10"),

    /** PKCS #7 MIME */
    APPLICATION_PKCS_7_MIME(arrayOf(".p7m"), "application/pkcs7-mime"),

    /** PKCS #7 Signature */
    APPLICATION_PKCS_7_SIGNATURE(arrayOf(".p7s"), "application/pkcs7-signature"),

    /** PKCS #8 */
    APPLICATION_PKCS_8(arrayOf(".p8"), "application/pkcs8"),

    /** PKCS #12 */
    APPLICATION_PKCS_12(arrayOf(".p12", ".pfx"), "application/x-pkcs12"),

    /** PostScript */
    APPLICATION_POSTSCRIPT(arrayOf(".ps"), "application/postscript"),

    /** RAR archive */
    APPLICATION_RAR(arrayOf(".rar"), "application/vnd.rar"),

    /** RDF/XML */
    APPLICATION_RDF_XML(arrayOf(".rdf"), "application/rdf+xml"),

    /** Rich Text Format (RTF) */
    APPLICATION_RTF(arrayOf(".rtf"), "application/rtf"),

    /** 7-zip archive */
    APPLICATION_SEVEN_ZIP(arrayOf(".7z"), "application/x-7z-compressed"),

    /** Bourne shell script */
    APPLICATION_SH(arrayOf(".sh"), "application/x-sh"),

    /** SMIL documents */
    APPLICATION_SMIL_XML(arrayOf(".smil", ".smi", ".sml"), "application/smil+xml"),

    /** SQL */
    APPLICATION_SQL(arrayOf(".sql"), "application/sql"),

    /** Small web format (SWF) or Adobe Flash document */
    APPLICATION_SWF(arrayOf(".swf"), "application/x-shockwave-flash"),

    /** Tape Archive (TAR) */
    APPLICATION_TAR(arrayOf(".tar"), "application/x-tar"),

    /** BZip Archive */
    APPLICATION_X_BZIP(arrayOf(".bz"), "application/x-bzip"),

    /** BZip2 Archive */
    APPLICATION_X_BZIP2(arrayOf(".bz2"), "application/x-bzip2"),

    /** XHTML */
    APPLICATION_XHTML_XML(arrayOf(".xhtml"), "application/xhtml+xml"),

    /** XML */
    APPLICATION_XML(arrayOf(".xml"), "application/xml"),

    /** XML DTD */
    APPLICATION_XML_DTD(arrayOf(".dtd", ".mod"), "application/xml-dtd"),

    /** XSLT Document */
    APPLICATION_XSLT_XML(arrayOf(".xsl", ".xslt"), "application/xslt+xml"),

    /** XUL */
    APPLICATION_XUL(arrayOf(".xul"), "application/vnd.mozilla.xul+xml"),

    /** Zip Archive */
    APPLICATION_ZIP(arrayOf(".zip"), "application/zip"),

    /** AAC audio */
    AUDIO_AAC(arrayOf(".aac"), "audio/aac"),

    /** Musical Instrument Digital Interface (MIDI) */
    AUDIO_MIDI(arrayOf(".mid", ".midi"), "audio/midi"),

    /** Musical Instrument Digital Interface (MIDI) */
    AUDIO_X_MIDI(arrayOf(".mid", ".midi"), "audio/x-midi"),

    /** Matroska Multimedia Container */
    AUDIO_MKA(arrayOf(".mka"), "audio/x-matroska"),

    /** MP3 audio */
    AUDIO_MP3(arrayOf(".mp3"), "audio/mpeg"),

    /** MP4 audio */
    AUDIO_MP4(arrayOf(".mp4"), "audio/mp4"),

    /** OGG audio */
    AUDIO_OGG(arrayOf(".oga"), "audio/ogg"),

    /** Opus audio */
    AUDIO_OPUS(arrayOf(".opus"), " audio/opus"),

    /** Waveform Audio Format */
    AUDIO_WAV(arrayOf(".wav"), "audio/wav"),

    /** WEBM audio */
    AUDIO_WEBM(arrayOf(".weba"), "audio/webm"),

    /** Windows OS/2 Bitmap Graphics */
    IMAGE_BMP(arrayOf(".bmp"), "image/bmp"),

    /** Graphics Interchange Format (GIF)*/
    IMAGE_GIF(arrayOf(".gif"), "image/gif"),

    /** Icon format */
    IMAGE_ICON(arrayOf(".ico"), "image/vnd.microsoft.icon"),

    /** JPEG images */
    IMAGE_JPEG(arrayOf(".jpg", ".jpeg"), "image/jpeg"),

    /** Portable Network Graphics */
    IMAGE_PNG(arrayOf(".png"), "image/png"),

    /** Scalable Vector Graphics (SVG) */
    IMAGE_SVG_XML(arrayOf(".svg"), "image/svg+xml"),

    /** Tagged Image File Format (TIFF) */
    IMAGE_TIFF(arrayOf(".tif", ".tiff"), "image/tiff"),

    /** WEBP image */
    IMAGE_WEBP(arrayOf(".webp"), "image/webp"),

    /** Cascading Style Sheets (CSS) */
    TEXT_CSS(arrayOf(".css"), "text/css"),

    /** Comma-separated values (CSV) */
    TEXT_CSV(arrayOf(".csv"), "text/csv"),

    /** HyperText Markup Language (HTML) */
    TEXT_HTML(arrayOf(".htm", ".html"), "text/html"),

    /** iCalendar format */
    TEXT_ICALENDAR(arrayOf(".ics"), "text/calendar"),

    /** JavaScript */
    TEXT_JAVASCRIPT_MODULE(arrayOf(".mjs"), "text/javascript"),

    /** Text, (generally ASCII or ISO 8859-n) */
    TEXT_PLAIN(arrayOf(".txt"), "text/plain"),

    /** Standard Generalized Markup Language */
    TEXT_SGML(arrayOf(".sgml"), "text/sgml"),

    /** YAML */
    TEXT_YAML(arrayOf(".yml", ".yaml"), "text/yaml"),

    /** AVI: Audio Video Interleave */
    VIDEO_AVI(arrayOf(".avi"), "video/x-msvideo"),

    /** 3GPP audio/video container */
    VIDEO_THREEGPP(arrayOf(".3gp"), "video/3gpp"),

    /** 3GPP2 audio/video container */
    VIDEO_THREEGPP2(arrayOf(".3g2"), "video/3gpp2"),

    /** Matroska Multimedia Container */
    VIDEO_MKV(arrayOf(".mkv"), "video/x-matroska"),

    /** MP4 video */
    VIDEO_MP4(arrayOf(".mp4"), "video/mp4"),

    /** MPEG Video */
    VIDEO_MPEG(arrayOf(".mpeg"), "video/mpeg"),

    /** MPEG transport stream */
    VIDEO_MPEG_TS(arrayOf(".ts"), "video/mp2t"),

    /** OGG video */
    VIDEO_OGG(arrayOf(".ogv"), "video/ogg"),

    /** QuickTime */
    VIDEO_QUICKTIME(arrayOf(".mov", ".qt"), "video/quicktime"),

    /** WEBM video */
    VIDEO_WEBM(arrayOf(".webm"), "video/webm"),

    /** OpenType font */
    FONT_OTF(arrayOf(".otf"), "font/otf"),

    /** TrueType Font */
    FONT_TTF(arrayOf(".ttf"), "font/ttf"),

    /** Web Open Font Format (WOFF) */
    FONT_WOFF(arrayOf(".woff"), "font/woff"),

    /** Web Open Font Format (WOFF) */
    FONT_WOFF2(arrayOf(".woff2"), "font/woff2");

    companion object {
        fun fromString(mimeType: String): MimeType
            = MimeType.values().last { it.mimeType == mimeType }
    }
}
