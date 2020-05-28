#version 400

in vec2 position ;
in vec2 textureCoords ;

out vec2 out_textureCoords ;

uniform mat4 projectionMatrix ;
uniform mat4 transformationMatrix ;
uniform float worldPosition ;

void main(void){

	gl_Position = projectionMatrix * transformationMatrix * vec4(position, worldPosition, 1.0 ) ;

	out_textureCoords = textureCoords;
}
