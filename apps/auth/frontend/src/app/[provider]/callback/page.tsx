import { useRouter } from 'next/navigation'

interface PageProps {
  params: {
    provider: string
  },
  searchParams: {
    [key: string]: string
  }
}



export default async function Callback({ params, searchParams }: PageProps) {
  const response = await fetch(`http://localhost:8081/api/v1/oauth/provider/${params.provider}/handle-authorization?code=${searchParams.code}`, { method: 'POST' })
  const code = await response.text()

  return code
}
