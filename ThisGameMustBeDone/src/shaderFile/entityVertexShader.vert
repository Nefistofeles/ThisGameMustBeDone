#version 330

in vec2 position ;
in vec2 textureCoords ;

out vec2 out_textureCoords ;

uniform mat4 projectionMatrix ;
uniform mat4 viewMatrix ;
uniform mat4 transformationMatrix ;
uniform float worldPosition ;

uniform vec2 textureSize ;
uniform vec2 offset ;


void main(void){

	gl_Position = projectionMatrix *viewMatrix * transformationMatrix * vec4(position, worldPosition, 1) ;

	out_textureCoords = (textureCoords / textureSize) + offset ;
}
