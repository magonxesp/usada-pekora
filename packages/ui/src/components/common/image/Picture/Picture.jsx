export function Picture(props) {
  return (
    <picture>
      <source srcSet={props.src} type={props.mimeType ?? ''} />
      <img src={props.src} className={props.className ?? ''} alt={props.alt ?? ''} />
    </picture>
  )
}
