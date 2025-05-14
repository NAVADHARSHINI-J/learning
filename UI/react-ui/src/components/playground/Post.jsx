import { useEffect, useState } from "react";

function Post() {
    const [posts, setPosts] = useState([]);

    //hook effect
    useEffect(() => {

        const getPosts = () => {
            //calling the api
            //fetch the ppost api
            fetch("https://jsonplaceholder.typicode.com/posts")
                //change the response into json 
                .then(response => response.json())
                //set the response in the posts
                .then(data => setPosts(data));
        }
        //call the getPosts() 
        //It is important to call the function useeffect is called itself but
        // function is not.we have to call it
        getPosts();
    }, [])

    return (
        <div>
            <h3>Welcome to Post</h3>
            {/* to iterate the array we have to use the map and don't use for forEach
            because within the map we can able to write a html but not in forEach
            map syntax is like return syntax */}
            {
                posts.map((p, index) => (
                    //it is very importnent to give index or p.id to the div
                    //orelse it will give us error
                    <div key={index}>
                        {p.id} <br />
                        {p.userId} <br />
                        {p.title} <br />
                        {p.body} <br />
                        <hr />
                    </div>
                )
                )
            }
        </div>
    )
}

export default Post;
//jsx = html + css + javascript 

/**
 * DOM: Document Object Model : web browser 
 * 
 * <div>
 *      <p></p>
 *      <p></p>
 * </div>
 * 
 * body 
 *    div
 *       p=5655656
 *       p=565776767 
 * 
 *   React User --> React DOM  --> Browser DOM 
 *  <div>
 *      <p key={p.id}></p>
 *      <p key={89898989}></p>
 * </div>
 * 
 */