interface PictureProps {
  src: string
  alt?: string
  mimeType?: string
  className?: string
}

export function Picture(props: PictureProps) {
  return (
    <picture>
      <source srcSet={props.src} type={props.mimeType ?? ''} />
      <img src={props.src} className={props.className ?? ''} alt={props.alt ?? ''} />
    </picture>
  )
}
