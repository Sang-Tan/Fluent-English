export enum MediaType {
  IMAGE = "image",
  VIDEO = "video",
  AUDIO = "audio",
  RAW = "raw",
}

export interface FileResource {
  mediaType?: MediaType;
}

export interface CloudinaryResource extends FileResource {
  type: "cloudinary";
  publicId: string;
  url: string;
}

export interface ExternalResource extends FileResource {
  type: "ext";
  url: string;
}
