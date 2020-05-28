#version 400

in vec2 out_textureCoords ;

uniform sampler2D textureSampler ;

out vec4 out_Color ;

void main(void){

	vec4 texture = texture(textureSampler, out_textureCoords) ;
	if(texture.a < 0.2){
		discard ;
	}

	out_Color = vec4(0.20, 0.43, 0.92, texture.a) ;
	//out_Color = vec4(1,0,1, 1) ;
}
