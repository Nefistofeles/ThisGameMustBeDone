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
uniform vec2 tex_multp ;


void main(void){

	gl_Position = projectionMatrix *viewMatrix * transformationMatrix * vec4(position, worldPosition, 1) ;

	out_textureCoords = ((textureCoords / textureSize) + offset)  ;
	out_textureCoords.x = out_textureCoords.x * tex_multp.x ;
	out_textureCoords.y = out_textureCoords.y * tex_multp.y ;
}
