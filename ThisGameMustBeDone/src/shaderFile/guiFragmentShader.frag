#version 400

in vec2 out_textureCoords ;

uniform sampler2D textureSampler ;

out vec4 out_Color ;

void main(void){

	vec4 texture = texture(textureSampler , out_textureCoords) ;

	out_Color = texture ;
}
