#version 330

in vec2 out_textureCoords ;

uniform sampler2D textureSampler2D ;

out vec4 out_Color ;

void main(void){

	vec4 textureColor = texture(textureSampler2D,out_textureCoords) ;

	if(textureColor.a < 0.2){
		discard ;
	}

	out_Color = textureColor ;

}
