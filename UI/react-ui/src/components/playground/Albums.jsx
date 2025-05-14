import { useEffect } from "react";
import { useSelector } from "react-redux";

function Albums() {

    const { albums } = useSelector(state => state.albums)
    useEffect(() => {
    }, [])
    return (
        <div>
            <h4>Albums</h4>
            {albums.map((a, index) => (
                <p key={index}>{a.title}</p>
            ))}
        </div>
    )
}
export default Albums;